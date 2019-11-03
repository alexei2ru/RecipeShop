
package org.magazin;
import java.io.Serializable;
import javax.persistence.*;
@Entity(name="Users")
@Table(name="Users")
public class User implements Serializable{
		static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name="id")
	    private int id;
	    @Column(name="username")
	    private String username;
	    @Column(name="password")
	    private String password;
	    @Column(name="role")
	    private String role;
	    public User() {}
	    public User(int id, String username, String password, String role)  {
	    		this.id = id;
	    		this.username = username;
	    		this.password = password;
	    		this.role = role;
	    		}
	    public User(int id) { this.id = id; }
	    
	    public int getId() { return id; }
	    
	    public void setId(int id) { this.id = id; }
	    
	    public String getName() { return username; }
	    
	    public void setName(String username) { this.username = username; }
	    
	    public String getPass() { return password; }
	    
	    public void setPass (String password) { this.password = password; }
	    
	    public String getRole() { return role; }
	    
	    public void setRole (String role) { this.role = role; }
}