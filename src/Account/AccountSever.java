package Account;

import javax.swing.*;

public interface AccountSever {
     void saveAccount(Account a);
     boolean checkAccount(Account a, JFrame parent);
     boolean exist(Account a, JFrame parent);;
}
