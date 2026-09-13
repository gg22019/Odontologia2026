
package sv.edu.ues_ingenieria_pp115_2026_salud.odontologia_sv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
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
    private TypedQuery<Clinica> query;
    
    @Mock
    private CriteriaBuilder cbMock;
    
    @Mock
    private CriteriaQuery cqMock;
    
    @Mock
    private Root rootMock;
    
    @Mock
  private TypedQuery<Clinica> queryMock;
    
    @InjectMocks
    private ClinicaDAO clinicaDao;
    
  
    
    //Clinica Activa
    private UUID idClinica;
    private Clinica clinica;
    
    
    //Clinica Inactiva
    private Clinica clinicaInactiva;
    
    @BeforeEach
    public  void setUpClass() {
        //Clinica Activo
      idClinica=UUID.randomUUID();
      clinica=new Clinica();
      
      clinica.setIdClinica(idClinica);
      clinica.setNombre("Clinica Dental Central");
      clinica.setTipo("PRINCIPAL");
      clinica.setActivo(true);
      clinica.setComentarios("Comentario de prueba");
      
      //Clinica Inactiva
      clinicaInactiva=new Clinica();
      
      clinicaInactiva.setIdClinica(UUID.randomUUID());
      clinicaInactiva.setNombre("Clinica Cerrada");
      clinicaInactiva.setTipo("SUCURSAL");
      clinicaInactiva.setActivo(false);
    
        
    }
    
    
    @Test
    public void testCrearExiste(){
            clinicaDao.crear(clinica);
            
            verify(emMock,times(1)).persist(clinica);
            verify(emMock,times(1)).flush();
    }
    
    @Test
    public void testCrear_null(){
        assertThrows(IllegalArgumentException.class,()->{
            clinicaDao.crear(null);
        });
        
        verify(emMock,never()).persist(any());
        verify(emMock,never()).flush();
        
    }
    
    @Test
    public void testCrear_falloAlGuardar(){
       // cuando se llame a persist(), lanza RuntimeException
         doThrow(new RuntimeException("Error de base de datos"))
        .when(emMock).persist(clinica);
       
         //Ejecutar y verificar que lanza IllegalStateException
         assertThrows(IllegalStateException.class,()->{
             clinicaDao.crear(clinica);
         });
         
         //Verificar que se llamó a persist() (aunque falló)
         verify(emMock,times(1)).persist(clinica);
         //Verificar que NO se llamó a flush() porque persist() falló
         verify(emMock,never()).flush();
    }
    
    @Test
   public void testModificar(){
       when(emMock.merge(clinica)).thenReturn(clinica);
       
       Clinica resultado=clinicaDao.modificar(clinica);
       
        assertNotNull(resultado);
        assertEquals(idClinica,clinica.getIdClinica());
        
        
        verify(emMock,times(1)).merge(clinica);
        verify(emMock,times(1)).flush();
       
       
   }
   
   @Test
   public void testModificar_null(){
       assertThrows(IllegalArgumentException.class, ()->{
           clinicaDao.modificar(null);
       });
       
       verify(emMock,never()).merge(any());
       verify(emMock,never()).flush();
       
   }
   
   @Test
   public void testModificar_fallo(){
       doThrow(new RuntimeException("Error al modificar el registro ")).when(emMock).merge(clinica);
       
       assertThrows(IllegalStateException.class, ()->{
           clinicaDao.modificar(clinica);
       });
       
       verify(emMock,times(1)).merge(any());
       verify(emMock,never()).flush();
   }

   @Test
   public void TestEliminar(){
       when(emMock.merge(clinica)).thenReturn(clinica);
       
       clinicaDao.eliminar(clinica);
       
       verify(emMock,times(1)).merge(clinica);
       verify(emMock,times(1)).remove(clinica);
       
   }
   
   @Test
   public void testEliminar_null(){
       assertThrows(IllegalArgumentException.class,()->{
           clinicaDao.eliminar(null);
       });
       
       verify(emMock,never()).merge((any()));
       verify(emMock,never()).remove(any());
   }

   
   @Test
   public void testEliminar_fallo(){
       when(emMock.merge(clinica)).thenReturn(clinica);
       doThrow(new RuntimeException("Error al eliminar el registro ")).when(emMock).remove(clinica);
       
       assertThrows(IllegalStateException.class , ()->{
           clinicaDao.eliminar(clinica);
       });
       
       verify(emMock,times(1)).merge(clinica);
       verify(emMock,times(1)).remove(clinica);
   }
   
   @Test
   public void testBuscarPorId(){
       when(emMock.find(Clinica.class, idClinica)).thenReturn(clinica);
       
       clinicaDao.buscarPorId(idClinica);
       
       assertEquals(idClinica,clinica.getIdClinica());
       
       
       verify(emMock,times(1)).find(Clinica.class,idClinica);
       
   }
   
   @Test
   public void testBuscarPorId_null(){
       assertThrows(IllegalArgumentException.class, ()->{
           clinicaDao.buscarPorId(null);
       });
       
       verify(emMock,never()).find(Clinica.class, null);
       
   }
   
   @Test
   public void testBuscarPorId_fallo(){
       doThrow(new RuntimeException("Error al buscar el registro por id")).when(emMock).find(Clinica.class, idClinica);
       
       assertThrows(IllegalStateException.class,()->{
          clinicaDao.buscarPorId(idClinica);
       });
       
       verify(emMock,times(1)).find(Clinica.class, idClinica);
       
       
   }
   
   @Test
   public void findAll(){
       List<Clinica> clinicasEsperadas = List.of(clinica, clinicaInactiva);

        when(emMock.getCriteriaBuilder()).thenReturn(cbMock);
        when(cbMock.createQuery(Clinica.class)).thenReturn(cqMock);
        when(cqMock.from(Clinica.class)).thenReturn(rootMock);
        when(cqMock.select(rootMock)).thenReturn(cqMock);
        when(emMock.createQuery(cqMock)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(clinicasEsperadas);

        List<Clinica> resultados = clinicaDao.findAll();

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertEquals("Clinica Dental Central", resultados.get(0).getNombre());

        verify(emMock, times(1)).getCriteriaBuilder();
        verify(cbMock, times(1)).createQuery(Clinica.class);
        verify(cqMock, times(1)).from(Clinica.class);
        verify(cqMock, times(1)).select(rootMock);
        verify(emMock, times(1)).createQuery(cqMock);
        verify(queryMock, times(1)).getResultList();
   }
    
}
