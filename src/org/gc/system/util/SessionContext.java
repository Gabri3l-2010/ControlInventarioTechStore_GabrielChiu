package org.gc.system.util;

public class SessionContext {
    private static SessionContext instance;
    private String usuario;
    private String rol;
    private long horaInicio;

    private SessionContext() {}

    public static synchronized SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public void iniciarSesion(String usuario, String rol) {
        this.usuario = usuario;
        this.rol = rol;
        this.horaInicio = System.currentTimeMillis();
    }

    public void cerrarSesion() {
        this.usuario = null;
        this.rol = null;
        this.horaInicio = 0;
    }

    public boolean isSesionActiva() {
        return usuario != null;
    }

    public String getUsuario() { return usuario; }
    public String getRol() { return rol; }
    public long getHoraInicio() { return horaInicio; }
}
