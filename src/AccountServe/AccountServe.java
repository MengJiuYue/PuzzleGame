package AccountServe;

import javax.swing.*;

public interface AccountServe {
     void saveAccount(Account a);
     boolean checkAccount(Account a, JFrame parent);
     boolean exist(Account a, JFrame parent);
     void alterAccount(Account a);
}
