package data.loader;

import java.util.List;

/**
 * Classe abstraite paramétrée responsable du chargement de données.
 *
 * <p>
 * Les sous-classes doivent proposer une définition de la méthode load()
 * </p>
 *
 * @author [Adam BELLIK]
 */
public abstract class DataLoader<T> {
    public abstract List<T> load();
}
