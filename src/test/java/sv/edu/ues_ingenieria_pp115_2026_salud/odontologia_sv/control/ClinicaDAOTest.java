/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sv.edu.ues_ingenieria_pp115_2026_salud.odontologia_sv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues_ingenieria_pp115_2026_salud.odontologia_sv.entity.Clinica;

@ExtendWith(MockitoExtension.class)
public class ClinicaDAOTest {
    
    @Mock
    private EntityManager emMock;

    @Mock
    private TypedQuery query ;
    
            
    @InjectMocks
    private  ClinicaDAO clinicaDao;
    
    private Clinica clinica ;
    private UUID idClinica ;
   private Clinica clinicaInactiva;
  
    
    @BeforeEach
    public void setUp() {
        
        //clinica activa
        idClinica=UUID.randomUUID();
        
        clinica=new Clinica();
        clinica.setIdClinica(idClinica);
        clinica.setNombre("Clinica Dental");
        clinica.setTipo("Principal");
        clinica.setActivo(true);
        clinica.setComentarios("Clinica principal de todos los servicios");
        
        //clinica inactiva
        clinicaInactiva =new Clinica();
        clinicaInactiva.setIdClinica(UUID.randomUUID());
        clinicaInactiva.setNombre("Clinica Cerrada");
        clinicaInactiva.setTipo("SUCURSAL");
        clinicaInactiva.setActivo(false);
        
        
        
    }
    
    @Test
   public void testBuscarPorId_existe(){
        when(emMock.find(Clinica.class, idClinica)).thenReturn(clinica);
        
        Clinica resultado=clinicaDao.buscarPorId(idClinica);
        
        assertNotNull(resultado);
        assertEquals(idClinica,clinica.getIdClinica());
        assertEquals("Clinica Dental",clinica.getNombre());
        
        assertEquals("Principal",clinica.getTipo());
        assertEquals(clinica.getActivo(),true);
        
        verify(emMock,times(1)).find(Clinica.class, idClinica);
       
       
   }
   
   @Test
   public void testBuscarPorId_null(){
       when(emMock.find(Clinica.class, idClinica)).thenReturn(null);
       
       Clinica resultado=clinicaDao.buscarPorId(idClinica);
       
       assertNull(resultado);
       
       verify(emMock,times(1)).find(Clinica.class, idClinica);
       
       
       
   }
   
    @Test
   public void testBuscarPorId_null_exception(){
       assertThrows(IllegalArgumentException.class,()->{
           clinicaDao.buscarPorId(null);
       });
       
       verify(emMock,never()).find(any(),any());

   }

   @Test
      public void testBuscarPorId_EntityManagerNull_execption(){
      
      ClinicaDAO daoSpy=spy(clinicaDao);
      
      doReturn(null).when(daoSpy).getEntityManager();
      
       assertThrows(IllegalStateException.class, ()->{
           daoSpy.buscarPorId(idClinica);
       });
       
       
   }
    

    
    
}
