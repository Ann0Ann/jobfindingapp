package project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import project.User;
import project.Job;
import project.Application;
import project.Store;
import project.AdminPanel;
import project.RecruiterPanel;
import project.JobseekerPanel;
public class JobPortalFrame extends JFrame {
private CardLayout cards;
private JPanel root;
private User currentUser;
// Panels
private LoginPanel loginPanel;
private AdminPanel adminPanel;
private RecruiterPanel recruiterPanel;
private JobseekerPanel jobseekerPanel;
public JobPortalFrame() {
setTitle("Job Portal");
setSize(800, 600);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Initialize store with data
Store.seed();
cards = new CardLayout();
root = new JPanel(cards);
add(root);
// Panels
loginPanel = new LoginPanel(this);
adminPanel = new AdminPanel(this);
recruiterPanel = new RecruiterPanel(this);
jobseekerPanel = new JobseekerPanel(this);
root.add(loginPanel, "Login");
root.add(adminPanel, "Admin");
root.add(recruiterPanel, "Recruiter");
root.add(jobseekerPanel, "Jobseeker");
showCard("Login");
}
public void setCurrentUser(User u) {
this.currentUser = u;
}
public User getCurrentUser() {
return currentUser;
}
public void showCard(String name) {
cards.show(root, name);
}
public static void main(String[] args) {
SwingUtilities.invokeLater(() -> {
new JobPortalFrame().setVisible(true);
});
}
}
class LoginPanel extends JPanel {
private JTextField nameField;
private JPasswordField passField;
public LoginPanel(JobPortalFrame app) {
setLayout(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 5, 5, 5);
JLabel nameLabel = new JLabel("Username:");
JLabel passLabel = new JLabel("Password:");
nameField = new JTextField(15);
passField = new JPasswordField(15);
JButton loginBtn = new JButton("Login");
gbc.gridx = 0; gbc.gridy = 0;
add(nameLabel, gbc);
gbc.gridx = 1;
add(nameField, gbc);
gbc.gridx = 0; gbc.gridy = 1;
add(passLabel, gbc);
gbc.gridx = 1;
add(passField, gbc);
gbc.gridx = 1; gbc.gridy = 2;
add(loginBtn, gbc);
// Login action
loginBtn.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
String name = nameField.getText().trim();
String pass = new String(passField.getPassword()).trim();
User user = Store.login(name, pass);
if (user != null) {
app.setCurrentUser(user);
JOptionPane.showMessageDialog(app,
"Login successful! Welcome " + user.getName());
switch (user.getRole()) {
case "Admin":
app.showCard("Admin");
break;
case "Recruiter":
app.showCard("Recruiter");
break;
case "Jobseeker":
app.showCard("Jobseeker");
break;
default:
JOptionPane.showMessageDialog(app, "Unknown role!");
break;
}
} else {
JOptionPane.showMessageDialog(app, "Invalid username or password!");
}
}
});
}
}