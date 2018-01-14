package entity;
import java.io.Serializable;
import javax.persistence.Entity; import javax.persistence.*;
@Entity
@Table(name = "tb_userinfo") 
public class Userinfo implements Serializable {
private static final long serialVersionUID = 2300044412175011558L;
@Column(name = "name", nullable = false) 
private String name;
 public String getName() { return name; }
 public void setName(String name) { this.name = name; }
@Column(name = "age", nullable = false) 
private String age;
 public String getAge() { return age; }
 public void setAge(String age) { this.age = age; }
@Column(name = "address", nullable = false) 
private String address;
 public String getAddress() { return address; }
 public void setAddress(String address) { this.address = address; }
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", nullable = false) 
private String id;
 public String getId() { return id; }
 public void setId(String id) { this.id = id; }
@Override
public String toString() {
return "Userinfo{"+"'name'='"+name+"'"+",'age'='"+age+"'"+",'address'='"+address+"'"+",'id'='"+id+"'"+'}';}
}
