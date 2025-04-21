package project.gestionprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.Repositories.ProjetRepo;
import project.gestionprojet.ServiceImpl.ProjetServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjetTest {

    @Mock
    private ProjetRepo projetRepo;

    @Mock
    private ProductBacklogRepo productBacklogRepo;

    @InjectMocks
    private ProjetServiceImpl projetService;

    @Captor
    private ArgumentCaptor<Projet> projetCaptor;

    private Projet projet;
    private ProjetDTO projetDTO;
    private List<Projet> projetList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        projet = new Projet();
        projet.setIdProjet(1);
        projet.setNomProjet("Projet Test");

        projetDTO = new ProjetDTO();
        projetDTO.setIdProjet(1);
        projetDTO.setNomProjet("Projet Test");

        projetList = new ArrayList<>();
        projetList.add(projet);

        Projet projet2 = new Projet();
        projet2.setIdProjet(2);
        projet2.setNomProjet("Projet Test 2");
        projetList.add(projet2);
    }

    @Nested
    @DisplayName("Tests pour addProjet")
    class AddProjetTests {

        @Test
        @DisplayName("Devrait ajouter un projet avec succès")
        void shouldAddProjetSuccessfully() {
            when(projetRepo.save(any(Projet.class))).thenReturn(projet);

            ProjetDTO result = projetService.addProjet(projetDTO);

            assertNotNull(result);
            assertEquals(projetDTO.getNomProjet(), result.getNomProjet());
            assertEquals(projet.getIdProjet(), result.getIdProjet());

            verify(projetRepo).save(projetCaptor.capture());
            Projet capturedProjet = projetCaptor.getValue();
            assertEquals(projetDTO.getNomProjet(), capturedProjet.getNomProjet());
        }

        @Test
        @DisplayName("Devrait lancer une exception quand la sauvegarde échoue")
        void shouldThrowExceptionWhenSaveFails() {
            when(projetRepo.save(any(Projet.class))).thenReturn(null);

            Exception exception = assertThrows(NullPointerException.class, () -> {
                projetService.addProjet(projetDTO);
            });

            verify(projetRepo).save(any(Projet.class));
        }
    }

    @Nested
    @DisplayName("Tests pour updateProjet")
    class UpdateProjetTests {

        @Test
        @DisplayName("Devrait mettre à jour un projet avec succès")
        void shouldUpdateProjetSuccessfully() {
            ProjetDTO updatedProjetDTO = new ProjetDTO();
            updatedProjetDTO.setIdProjet(1);
            updatedProjetDTO.setNomProjet("Projet Test Modifié");

            when(projetRepo.findById(anyInt())).thenReturn(Optional.of(projet));
            when(projetRepo.save(any(Projet.class))).thenReturn(projet);

            ProjetDTO result = projetService.updateProjet(1, updatedProjetDTO);

            assertNotNull(result);
            assertEquals(updatedProjetDTO.getNomProjet(), result.getNomProjet());

            verify(projetRepo).findById(1);
            verify(projetRepo).save(any(Projet.class));
        }

        @Test
        @DisplayName("Devrait lancer une assertion error quand le projet n'existe pas")
        void shouldThrowAssertionErrorWhenProjectNotExists() {
            when(projetRepo.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(AssertionError.class, () -> {
                projetService.updateProjet(999, projetDTO);
            });

            verify(projetRepo).findById(999);
            verify(projetRepo, never()).save(any(Projet.class));
        }

        @Test
        @DisplayName("Ne devrait pas mettre à jour le nom si identique")
        void shouldNotUpdateNameIfIdentical() {
            when(projetRepo.findById(anyInt())).thenReturn(Optional.of(projet));
            when(projetRepo.save(any(Projet.class))).thenReturn(projet);

            ProjetDTO result = projetService.updateProjet(1, projetDTO);

            assertNotNull(result);
            assertEquals(projetDTO.getNomProjet(), result.getNomProjet());

            verify(projetRepo).findById(1);
            verify(projetRepo).save(any(Projet.class));
        }
    }

    @Nested
    @DisplayName("Tests pour getProjet")
    class GetProjetTests {

        @Test
        @DisplayName("Devrait récupérer un projet par ID avec succès")
        void shouldGetProjetByIdSuccessfully() {
            when(projetRepo.findById(anyInt())).thenReturn(Optional.of(projet));

            ProjetDTO result = projetService.getProjet(1);

            assertNotNull(result);
            assertEquals(projet.getIdProjet(), result.getIdProjet());
            assertEquals(projet.getNomProjet(), result.getNomProjet());

            verify(projetRepo).findById(1);
        }

        @Test
        @DisplayName("Devrait lancer une exception quand le projet n'existe pas")
        void shouldThrowExceptionWhenProjectNotExists() {
            when(projetRepo.findById(anyInt())).thenReturn(Optional.empty());

            Exception exception = assertThrows(IllegalStateException.class, () -> {
                projetService.getProjet(999);
            });

            assertEquals("le projet n'existe pas", exception.getMessage());
            verify(projetRepo).findById(999);
        }
    }

    @Nested
    @DisplayName("Tests pour getProjets")
    class GetProjetsTests {

        @Test
        @DisplayName("Devrait récupérer tous les projets avec succès")
        void shouldGetAllProjetsSuccessfully() {
            when(projetRepo.findAll()).thenReturn(projetList);

            List<ProjetDTO> results = projetService.getProjets();

            assertNotNull(results);
            assertEquals(2, results.size());
            assertEquals(projetList.get(0).getIdProjet(), results.get(0).getIdProjet());
            assertEquals(projetList.get(0).getNomProjet(), results.get(0).getNomProjet());
            assertEquals(projetList.get(1).getIdProjet(), results.get(1).getIdProjet());
            assertEquals(projetList.get(1).getNomProjet(), results.get(1).getNomProjet());

            verify(projetRepo).findAll();
        }

        @Test
        @DisplayName("Devrait retourner une liste vide quand aucun projet n'existe")
        void shouldReturnEmptyListWhenNoProjects() {
            when(projetRepo.findAll()).thenReturn(new ArrayList<>());

            List<ProjetDTO> results = projetService.getProjets();

            assertNotNull(results);
            assertTrue(results.isEmpty());

            verify(projetRepo).findAll();
        }
    }

    @Nested
    @DisplayName("Tests pour deleteProjet")
    class DeleteProjetTests {

        @Test
        @DisplayName("Devrait supprimer un projet avec succès")
        void shouldDeleteProjetSuccessfully() {
            when(projetRepo.findById(anyInt())).thenReturn(Optional.of(projet));
            doNothing().when(productBacklogRepo).deleteByProjetId(anyInt());
            doNothing().when(projetRepo).deleteById(anyInt());

            projetService.deleteProjet(1);

            verify(productBacklogRepo).deleteByProjetId(1);
            verify(projetRepo).findById(1);
            verify(projetRepo).deleteById(1);
        }

        @Test
        @DisplayName("Devrait lancer une exception quand le projet à supprimer n'existe pas")
        void shouldThrowExceptionWhenProjectToDeleteNotExists() {
            when(projetRepo.findById(anyInt())).thenReturn(Optional.empty());
            doNothing().when(productBacklogRepo).deleteByProjetId(anyInt());

            Exception exception = assertThrows(IllegalStateException.class, () -> {
                projetService.deleteProjet(999);
            });

            assertEquals("projet n'existe pas", exception.getMessage());

            verify(productBacklogRepo).deleteByProjetId(999);
            verify(projetRepo).findById(999);
            verify(projetRepo, never()).deleteById(anyInt());
        }
    }

    @Nested
    @DisplayName("Tests pour getProjetByName")
    class GetProjetByNameTests {

        @Test
        @DisplayName("Devrait récupérer un projet par nom avec succès")
        void shouldGetProjetByNameSuccessfully() {
            when(projetRepo.findByNomProjet(anyString())).thenReturn(projet);

            ProjetDTO result = projetService.getProjetByName("Projet Test");

            assertNotNull(result);
            assertEquals(projet.getIdProjet(), result.getIdProjet());
            assertEquals(projet.getNomProjet(), result.getNomProjet());

            verify(projetRepo).findByNomProjet("Projet Test");
        }

        @Test
        @DisplayName("Devrait lancer une exception quand le projet n'existe pas par nom")
        void shouldThrowExceptionWhenProjectNotExistsByName() {
            when(projetRepo.findByNomProjet(anyString())).thenReturn(null);

            Exception exception = assertThrows(IllegalStateException.class, () -> {
                projetService.getProjetByName("Projet Inexistant");
            });

            assertEquals("projet n'existe pas", exception.getMessage());
            verify(projetRepo).findByNomProjet("Projet Inexistant");
        }
    }

    @Test
    @DisplayName("Test d'intégration des fonctionnalités")
    void testIntegrationOfFunctionalities() {
        reset(projetRepo, productBacklogRepo);

        when(projetRepo.save(any(Projet.class))).thenReturn(projet);
        when(projetRepo.findById(1)).thenReturn(Optional.of(projet));
        doNothing().when(productBacklogRepo).deleteByProjetId(1);
        doNothing().when(projetRepo).deleteById(1);

        ProjetDTO addedProject = projetService.addProjet(projetDTO);
        ProjetDTO retrievedProject = projetService.getProjet(1);
        projetService.deleteProjet(1);

        assertEquals(1, addedProject.getIdProjet());
        assertEquals("Projet Test", retrievedProject.getNomProjet());

        verify(projetRepo).save(any(Projet.class));
        verify(productBacklogRepo).deleteByProjetId(1);
        verify(projetRepo).deleteById(1);
    }
}