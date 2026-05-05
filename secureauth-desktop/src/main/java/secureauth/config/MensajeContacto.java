package secureauth.config;

import java.time.LocalDateTime;

/**
 * POJO para mensajes de contacto.
 */
public class MensajeContacto {
    private Long id;
    private String nombre;
    private String email;
    private String asunto;
    private String mensaje;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "MensajeContacto{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               ", asunto='" + asunto + '\'' +
               '}';
    }
}