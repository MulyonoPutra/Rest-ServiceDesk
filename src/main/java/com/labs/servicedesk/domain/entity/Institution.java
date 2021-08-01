package com.labs.servicedesk.domain.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Institution.
 */
@Entity
@Table(name = "institution")
public class Institution implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "instance_name", nullable = false)
    private String instanceName;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "contact_number", nullable = false)
  private String contactNumber;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Institution id(Long id) {
    this.id = id;
    return this;
  }

  public String getInstanceName() {
    return this.instanceName;
  }

  public Institution instanceName(String instanceName) {
    this.instanceName = instanceName;
    return this;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

  public String getAddress() {
    return this.address;
  }

  public Institution address(String address) {
    this.address = address;
    return this;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getContactNumber() {
    return this.contactNumber;
  }

  public Institution contactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
    return this;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Institution)) {
      return false;
    }
    return id != null && id.equals(((Institution) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Institution{" +
                "id=" + getId() +
                ", instanceName='" + getInstanceName() + "'" +
                ", address='" + getAddress() + "'" +
                ", contactNumber='" + getContactNumber() + "'" +
                "}";
    }
}
