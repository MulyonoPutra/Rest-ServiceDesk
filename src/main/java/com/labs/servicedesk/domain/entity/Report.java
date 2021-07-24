package com.labs.servicedesk.domain.entity;

import com.labs.servicedesk.domain.enumeration.ReportType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
public class Report implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "title", nullable = false)
  private String title;

  @Lob
  @Column(name = "content", nullable = false)
  private String content;

  @NotNull
  @Column(name = "date", nullable = false)
  private Instant date;

  @Lob
  @Column(name = "images", nullable = false)
  private byte[] images;

  @Column(name = "images_content_type", nullable = false)
  private String imagesContentType;

  @NotNull
  @Column(name = "location", nullable = false)
  private String location;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private ReportType type;

  @ManyToOne
  private Category category;

  @ManyToOne
  private Institution institution;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Report id(Long id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public Report title(String title) {
    this.title = title;
    return this;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public Report content(String content) {
    this.content = content;
    return this;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Instant getDate() {
    return this.date;
  }

  public Report date(Instant date) {
    this.date = date;
    return this;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public byte[] getImages() {
    return this.images;
  }

  public Report images(byte[] images) {
    this.images = images;
    return this;
  }

  public void setImages(byte[] images) {
    this.images = images;
  }

  public String getImagesContentType() {
    return this.imagesContentType;
  }

  public Report imagesContentType(String imagesContentType) {
    this.imagesContentType = imagesContentType;
    return this;
  }

  public void setImagesContentType(String imagesContentType) {
    this.imagesContentType = imagesContentType;
  }

  public String getLocation() {
    return this.location;
  }

  public Report location(String location) {
    this.location = location;
    return this;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public ReportType getType() {
    return this.type;
  }

  public Report type(ReportType type) {
    this.type = type;
    return this;
  }

  public void setType(ReportType type) {
    this.type = type;
  }

  public Category getCategory() {
    return this.category;
  }

  public Report category(Category category) {
    this.setCategory(category);
    return this;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Institution getInstitution() {
    return this.institution;
  }

  public Report institution(Institution institution) {
    this.setInstitution(institution);
    return this;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Report)) {
      return false;
    }
    return id != null && id.equals(((Report) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Report{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                ", content='" + getContent() + "'" +
                ", date='" + getDate() + "'" +
                ", images='" + getImages() + "'" +
                ", imagesContentType='" + getImagesContentType() + "'" +
                ", location='" + getLocation() + "'" +
                ", type='" + getType() + "'" +
                "}";
    }
}
