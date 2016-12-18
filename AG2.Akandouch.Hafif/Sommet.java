import java.util.Objects;

public class Sommet<T> {
    private T valeur;
    private boolean visite;
    
    public Sommet(T valeur, boolean visite) {
        this.valeur = valeur;
        this.visite = visite;
    }

    /**
     * @return the valeur
     */
    public T getValeur() {
        return valeur;
    }

    /**
     * @param valeur the valeur to set
     */
    public void setValeur(T valeur) {
        this.valeur = valeur;
    }

    /**
     * @return the visite
     */
    public boolean isVisite() {
        return visite;
    }

    /**
     * @param visite the visite to set
     */
    public void setVisite(boolean visite) {
        this.visite = visite;
    }

    @Override
    public String toString() {
        return valeur.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Sommet && this.valeur.equals(((Sommet) obj).valeur));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.valeur);
        return hash;
    }
}
