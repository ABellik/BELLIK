package model.ownership;

import java.util.ArrayList;
import java.util.List;

public interface Appropriable {
    public void addShare(Ownership o);
    public void removeShare(Ownership o);
    public List<Ownership> getShares();
}
