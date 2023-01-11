package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Serveur.
 */
@Entity
@Table(name = "serveur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Serveur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_serveurs")
    @SequenceGenerator(name = "seq_serveurs", sequenceName = "seq_serveurs", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_hebergeur")
    private String nomHebergeur;

    @Column(name = "nom_serveur")
    private String nomServeur;

    @Column(name = "arch")
    private String arch;

    @Column(name = "cpu_nombre")
    private Long cpuNombre;

    @Column(name = "ram")
    private Long ram;

    @Column(name = "max_sire")
    private Long maxSire;

    @Column(name = "type")
    private String type;

    @Column(name = "price_monthly")
    private Long priceMonthly;

    @Column(name = "hourly_price")
    private Long hourlyPrice;

    @Column(name = "ipv_6")
    private Boolean ipv6;

    @Column(name = "band_width_internal")
    private Long bandWidthInternal;

    @Column(name = "band_width_external")
    private Long bandWidthExternal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Serveur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomHebergeur() {
        return this.nomHebergeur;
    }

    public Serveur nomHebergeur(String nomHebergeur) {
        this.setNomHebergeur(nomHebergeur);
        return this;
    }

    public void setNomHebergeur(String nomHebergeur) {
        this.nomHebergeur = nomHebergeur;
    }

    public String getNomServeur() {
        return this.nomServeur;
    }

    public Serveur nomServeur(String nomServeur) {
        this.setNomServeur(nomServeur);
        return this;
    }

    public void setNomServeur(String nomServeur) {
        this.nomServeur = nomServeur;
    }

    public String getArch() {
        return this.arch;
    }

    public Serveur arch(String arch) {
        this.setArch(arch);
        return this;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public Long getCpuNombre() {
        return this.cpuNombre;
    }

    public Serveur cpuNombre(Long cpuNombre) {
        this.setCpuNombre(cpuNombre);
        return this;
    }

    public void setCpuNombre(Long cpuNombre) {
        this.cpuNombre = cpuNombre;
    }

    public Long getRam() {
        return this.ram;
    }

    public Serveur ram(Long ram) {
        this.setRam(ram);
        return this;
    }

    public void setRam(Long ram) {
        this.ram = ram;
    }

    public Long getMaxSire() {
        return this.maxSire;
    }

    public Serveur maxSire(Long maxSire) {
        this.setMaxSire(maxSire);
        return this;
    }

    public void setMaxSire(Long maxSire) {
        this.maxSire = maxSire;
    }

    public String getType() {
        return this.type;
    }

    public Serveur type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPriceMonthly() {
        return this.priceMonthly;
    }

    public Serveur priceMonthly(Long priceMonthly) {
        this.setPriceMonthly(priceMonthly);
        return this;
    }

    public void setPriceMonthly(Long priceMonthly) {
        this.priceMonthly = priceMonthly;
    }

    public Long getHourlyPrice() {
        return this.hourlyPrice;
    }

    public Serveur hourlyPrice(Long hourlyPrice) {
        this.setHourlyPrice(hourlyPrice);
        return this;
    }

    public void setHourlyPrice(Long hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public Boolean getIpv6() {
        return this.ipv6;
    }

    public Serveur ipv6(Boolean ipv6) {
        this.setIpv6(ipv6);
        return this;
    }

    public void setIpv6(Boolean ipv6) {
        this.ipv6 = ipv6;
    }

    public Long getBandWidthInternal() {
        return this.bandWidthInternal;
    }

    public Serveur bandWidthInternal(Long bandWidthInternal) {
        this.setBandWidthInternal(bandWidthInternal);
        return this;
    }

    public void setBandWidthInternal(Long bandWidthInternal) {
        this.bandWidthInternal = bandWidthInternal;
    }

    public Long getBandWidthExternal() {
        return this.bandWidthExternal;
    }

    public Serveur bandWidthExternal(Long bandWidthExternal) {
        this.setBandWidthExternal(bandWidthExternal);
        return this;
    }

    public void setBandWidthExternal(Long bandWidthExternal) {
        this.bandWidthExternal = bandWidthExternal;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Serveur)) {
            return false;
        }
        return id != null && id.equals(((Serveur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Serveur{" +
            "id=" + getId() +
            ", nomHebergeur='" + getNomHebergeur() + "'" +
            ", nomServeur='" + getNomServeur() + "'" +
            ", arch='" + getArch() + "'" +
            ", cpuNombre=" + getCpuNombre() +
            ", ram=" + getRam() +
            ", maxSire=" + getMaxSire() +
            ", type='" + getType() + "'" +
            ", priceMonthly=" + getPriceMonthly() +
            ", hourlyPrice=" + getHourlyPrice() +
            ", ipv6='" + getIpv6() + "'" +
            ", bandWidthInternal=" + getBandWidthInternal() +
            ", bandWidthExternal=" + getBandWidthExternal() +
            "}";
    }
}
