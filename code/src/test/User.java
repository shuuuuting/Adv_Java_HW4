/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zhangshuting
 */
@Entity
@Table(name = "ENTRYDATA")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "User.findAll", query = "SELECT c FROM User c")
	, @NamedQuery(name = "User.findByName", query = "SELECT c FROM User c WHERE c.username = :username")
	, @NamedQuery(name = "User.findByPassword", query = "SELECT c FROM User c WHERE c.password = :password")
	, @NamedQuery(name = "User.findByEntry", query = "SELECT c FROM User c WHERE c.entrydate = :entry")})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "USERNAME")
	private String username;
	@Basic(optional = false)
    @Column(name = "PASSWORD")
	private String password;
	@Basic(optional = false)
    @Column(name = "ENTRYDATE")
    @Temporal(TemporalType.TIMESTAMP)
	private Date entrydate;

	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public User(String username, String password, Date entry) {
		this.username = username;
		this.password = password;
		this.entrydate = entry;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getEntry() {
		return entrydate;
	}

	public void setEntry(Date entry) {
		this.entrydate = entry;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (username != null ? username.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "test.User[ user=" + username + " ]";
	}
	
}
