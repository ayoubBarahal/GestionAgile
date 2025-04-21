package project.gestionprojet.Configuration;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.DTO.SprintBacklogDTO;
import project.gestionprojet.Entities.*;
import project.gestionprojet.Repositories.*;
import project.gestionprojet.Service.EpicService;
import project.gestionprojet.Service.ProductBacklogService;
import project.gestionprojet.Service.ProjectService;
import project.gestionprojet.Service.SprintBacklogService;

import java.util.Date;

/**
 * Classe d'initialisation des données pour l'environnement de test
 * Utilisez le profil "dev" pour activer cette initialisation
 * Exemple: spring.profiles.active=dev dans application.properties
 */
@Configuration
@Profile("dev") // Cette configuration ne sera active que si le profil "dev" est activé
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjetRepo projetRepo;

    @Autowired
    private ProductBacklogRepo productBacklogRepo;

    @Autowired
    private EpicRepo epicRepo;

    @Autowired
    private SprintBacklogRepo sprintBacklogRepo;

    @Autowired
    private SprintRepo sprintRepo;

    @Autowired
    private UserStoryRepo userStoryRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProductBacklogService productBacklogService;

    @Autowired
    private SprintBacklogService sprintBacklogService;

    @Autowired
    private EpicService epicService;

    /**
     * Méthode qui s'exécute au démarrage de l'application pour initialiser les données
     */
    @Bean
    @Transactional
    public CommandLineRunner initData() {
        return args -> {
            // Nettoyage des tables existantes (dans l'ordre pour respecter les contraintes d'intégrité)
            taskRepo.deleteAll();
            userStoryRepo.deleteAll();
            epicRepo.deleteAll();
            sprintRepo.deleteAll();
            sprintBacklogRepo.deleteAll();
            productBacklogRepo.deleteAll();
            projetRepo.deleteAll();
            userRepository.deleteAll();

            System.out.println("=== Initialisation des données ===");

            // Création des utilisateurs
            initUsers();

            // Création des projets et des backlogs
            initProjetsAndBacklogs();

            // Création des epics, sprints et user stories
            initEpicsAndSprints();

            // Création des tâches
            initTasks();

            System.out.println("=== Initialisation terminée ===");
        };
    }

    /**
     * Initialisation des utilisateurs avec différents rôles
     */
    private void initUsers() {
        System.out.println("Création des utilisateurs...");

        // Utilisateur Product Owner
        User productOwner = new User(
                "product",
                "product@example.com",
                passwordEncoder.encode("password")
        );
        productOwner.setRole(ERole.ROLE_PRODUCT_OWNER);
        userRepository.save(productOwner);

        // Utilisateur Scrum Master
        User scrumMaster = new User(
                "scrum",
                "scrum@example.com",
                passwordEncoder.encode("password")
        );
        scrumMaster.setRole(ERole.ROLE_SCRUM_MASTER);
        userRepository.save(scrumMaster);

        // Utilisateur Développeur
        User developer = new User(
                "dev",
                "dev@example.com",
                passwordEncoder.encode("password")
        );
        developer.setRole(ERole.ROLE_DEVELOPER);
        userRepository.save(developer);

        System.out.println("Utilisateurs créés avec succès !");
    }

    /**
     * Initialisation des projets et des backlogs
     */
    private void initProjetsAndBacklogs() {
        System.out.println("Création des projets et backlogs...");

        // Créer 3 projets
        for (int i = 1; i <= 3; i++) {
            // Création du projet via le service
            ProjetDTO projetDTO = new ProjetDTO();
            projetDTO.setNomProjet("Projet " + i);
            ProjetDTO savedProjet = projectService.addProjet(projetDTO);

            // Création du product backlog pour ce projet
            ProductBacklogDTO productBacklogDTO = new ProductBacklogDTO();
            productBacklogDTO.setNom("Backlog du projet " + i);
            productBacklogDTO.setIdProjet(savedProjet.getIdProjet());
            try {
                productBacklogService.addProductBacklog(productBacklogDTO);
            } catch (RuntimeException e) {
                System.out.println("Erreur lors de la création du backlog: " + e.getMessage());
            }

            // Création du sprint backlog pour ce projet
            SprintBacklogDTO sprintBacklogDTO = new SprintBacklogDTO();
            sprintBacklogDTO.setNom("Sprint Backlog " + i);
            sprintBacklogDTO.setDescription("Description du sprint backlog " + i);
            SprintBacklogDTO savedSprintBacklog = sprintBacklogService.createSprintBacklog(sprintBacklogDTO);

            System.out.println("Projet " + i + " créé avec son backlog !");
        }
    }

    /**
     * Initialisation des epics, sprints et user stories
     */
    private void initEpicsAndSprints() {
        System.out.println("Création des epics, sprints et user stories...");

        // Récupérer tous les backlogs
        productBacklogRepo.findAll().forEach(productBacklog -> {
            // Pour chaque product backlog, créer 2 epics
            for (int i = 1; i <= 2; i++) {
                EpicDTO epicDTO = new EpicDTO();
                epicDTO.setTitre("Epic " + i + " du backlog " + productBacklog.getIdProductBacklog());
                epicDTO.setDescription("Description de l'epic " + i);
                epicDTO.setIdProductBacklog(productBacklog.getIdProductBacklog());

                // Trouver un sprint backlog à associer (on prend le premier disponible)
                SprintBacklog sprintBacklog = sprintBacklogRepo.findAll().get(0);
                epicDTO.setIdSprintBacklog(sprintBacklog.getIdSprintBacklog());

                try {
                    EpicDTO savedEpic = epicService.createEpic(epicDTO);

                    // Créer des user stories pour cet epic
                    createUserStories(savedEpic.getIdEpic());
                } catch (Exception e) {
                    System.out.println("Erreur lors de la création de l'epic: " + e.getMessage());
                }
            }

            // Créer des sprints associés au premier sprint backlog
            if (!sprintBacklogRepo.findAll().isEmpty()) {
                createSprints(sprintBacklogRepo.findAll().get(0).getIdSprintBacklog());
            }
        });
    }

    /**
     * Création de user stories pour un epic donné
     */
    private void createUserStories(int epicId) {
        // Récupérer l'epic
        Epic epic = epicRepo.findById(epicId).orElse(null);
        if (epic == null) return;

        // Créer 3 user stories pour cet epic
        for (int i = 1; i <= 3; i++) {
            UserStory userStory = new UserStory();
            userStory.setTitre("User Story " + i + " de l'epic " + epicId);
            userStory.setDescription("Description de la user story " + i);
            userStory.setDesire("En tant qu'utilisateur, je souhaite...");
            userStory.setGoal("Afin de...");
            userStory.setRole("Utilisateur");
            userStory.setPriority(Priority.values()[i % Priority.values().length]);
            userStory.setStatus(Status.values()[i % Status.values().length]);
            userStory.setEpic(epic);

            userStoryRepo.save(userStory);
        }
    }

    /**
     * Création de sprints pour un sprint backlog donné
     */
    private void createSprints(int sprintBacklogId) {
        // Récupérer le sprint backlog
        SprintBacklog sprintBacklog = sprintBacklogRepo.findById(sprintBacklogId).orElse(null);
        if (sprintBacklog == null) return;

        // Date actuelle
        Date now = new Date();

        // Créer 3 sprints
        for (int i = 1; i <= 3; i++) {
            Sprint sprint = new Sprint();
            sprint.setNomSprint("Sprint " + i);

            // Dates: premier sprint commence maintenant, les autres suivent
            Date startDate = new Date(now.getTime() + (i-1) * 14 * 24 * 60 * 60 * 1000L); // i-1 périodes de 14 jours
            Date endDate = new Date(startDate.getTime() + 14 * 24 * 60 * 60 * 1000L); // 14 jours après

            sprint.setDateDebut(startDate);
            sprint.setDateFin(endDate);
            sprint.setSprintBacklog(sprintBacklog);

            sprintRepo.save(sprint);
        }
    }

    /**
     * Initialisation des tâches liées aux user stories
     */
    private void initTasks() {
        System.out.println("Création des tâches...");

        // Pour chaque user story, créer des tâches
        userStoryRepo.findAll().forEach(userStory -> {
            // Créer 2-4 tâches par user story
            int taskCount = 2 + (int)(Math.random() * 3);
            for (int i = 1; i <= taskCount; i++) {
                Task task = new Task();
                task.setTitle("Tâche " + i + " de " + userStory.getTitre());
                task.setDescription("Description de la tâche " + i);
                task.setStatus(Status.values()[i % Status.values().length]);
                task.setUserStory(userStory);

                taskRepo.save(task);
            }
        });
    }
}
