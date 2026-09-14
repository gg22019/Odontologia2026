
package sv.edu.ues_ingenieria_pp115_2026_salud.odontologia_sv.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped
public class SesionUsuario implements Serializable{
    // Idiomas disponibles: nombre → Locale 
    private Map<String, Locale> idiomasDisponibles = new HashMap<>();
    
    // Idioma actualmente seleccionado (nombre legible)
    private String idiomaSeleccionado = "es";
    
    // ✅ Locale actual (persiste en la sesión) 
    private Locale locale = new Locale("es", "SV");

    @Inject
    private  FacesContext facesContext;

    @PostConstruct
    public void inicializador() {
        Locale espa = new Locale("es", "SV");
        Locale ingl=new Locale("en","US");
        idiomasDisponibles.put("Español", espa);
        idiomasDisponibles.put("English", ingl);
        
        this.idiomaSeleccionado = "Español";
        this.locale = espa;
    }

    /**
     * Cambia el idioma de la aplicación.
     */
    public void cambiarIdioma(AjaxBehaviorEvent event) {
        try {
            Locale nuevoLocale = idiomasDisponibles.get(idiomaSeleccionado);
            
            if (nuevoLocale != null) {
                this.locale = nuevoLocale;
                
                if (facesContext != null && facesContext.getViewRoot() != null) {
                    facesContext.getViewRoot().setLocale(nuevoLocale);
                }
                
                System.out.println("✅ Idioma cambiado a: " + nuevoLocale);
            }
        } catch (Exception ex) {
            System.err.println("❌ Error al cambiar idioma: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
   
    

    // =============================================
    // GETTERS Y SETTERS
    // =============================================

    public Map<String, Locale> getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    public void setIdiomasDisponibles(Map<String, Locale> idiomasDisponibles) {
        this.idiomasDisponibles = idiomasDisponibles;
    }

    public String getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    public void setIdiomaSeleccionado(String idiomaSeleccionado) {
        this.idiomaSeleccionado = idiomaSeleccionado;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }
}
