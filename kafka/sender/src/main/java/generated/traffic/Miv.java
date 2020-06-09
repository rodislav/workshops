//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.09 at 02:01:58 PM CEST 
//


package generated.traffic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Meten-in-Vlaanderen (MIV) Loop-based traffic data (1-minute data).
 *                     - http://opendata.vlaanderen.be/dataset/minuutwaarden-verkeersmetingen-vlaanderen
 *                     - The data is provided by the inductive detection loops, mainly on highways in Flanders.
 *                     - The data controllers are Agentschap Wegen en Verkeer (Roads and Traffic Agency,
 *                     http://www.wegenenverkeer.be)
 *                     and Vlaams Verkeerscentrum (the Flemish Traffic Centre, http://www.verkeerscentrum.be).
 *                     - The traffic data contained are p.e. the number of vehicles and the average speeds, divided in 5
 *                     vehicle classes, aggregated per minute.
 *                     - The data is updated every minute.
 *                     - The configuration data (location, ...) of the measurement points is available at
 *                     http://miv.opendata.belfla.be/miv/configuratie/xml
 *                     - The traffic data is available at http://miv.opendata.belfla.be/miv/verkeersdata
 *                     - The xsd-schemes document all data fields and are available at:
 *                     http://miv.opendata.belfla.be/miv-config.xsd
 *                     http://miv.opendata.belfla.be/miv-verkeersdata.xsd
 *                     - DatexII version of this data is planned to follow later in 2017.
 *                     - Feedback and suggestions for improvement: peter.lewyllie@mow.vlaanderen.be, reference:
 *                     OPENDATA.MIV
 *                     - Availability and liability: see art. 8 of the license.
 *                     Unavailability of this dataset can be notified to vtc.meldingen@vlaanderen.be (Dutch only),
 *                     reference: OPENDATA.MIV
 *                     - License: Modellicentie Gratis Hergebruik - v1.0
 *                 
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tijd_publicatie" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tijd_laatste_config_wijziging" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="meetpunt" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="lve_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="tijd_waarneming" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="tijd_laatst_gewijzigd" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="actueel_publicatie" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="beschikbaar" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="defect" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="geldig" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                         &lt;enumeration value="3"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="meetdata" maxOccurs="5" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="verkeersintensiteit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="voertuigsnelheid_rekenkundig" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="voertuigsnelheid_harmonisch" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="klasse_id" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                 &lt;enumeration value="1"/>
 *                                 &lt;enumeration value="2"/>
 *                                 &lt;enumeration value="3"/>
 *                                 &lt;enumeration value="4"/>
 *                                 &lt;enumeration value="5"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="rekendata" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="bezettingsgraad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="beschikbaarheidsgraad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="onrustigheid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="unieke_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="beschrijvende_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="schemaVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tijdPublicatie",
    "tijdLaatsteConfigWijziging",
    "meetpunt"
})
@XmlRootElement(name = "miv")
public class Miv {

    @XmlElement(name = "tijd_publicatie", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tijdPublicatie;
    @XmlElement(name = "tijd_laatste_config_wijziging")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tijdLaatsteConfigWijziging;
    protected List<Miv.Meetpunt> meetpunt;
    @XmlAttribute(name = "schemaVersion", required = true)
    protected String schemaVersion;

    /**
     * Gets the value of the tijdPublicatie property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdPublicatie() {
        return tijdPublicatie;
    }

    /**
     * Sets the value of the tijdPublicatie property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdPublicatie(XMLGregorianCalendar value) {
        this.tijdPublicatie = value;
    }

    /**
     * Gets the value of the tijdLaatsteConfigWijziging property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdLaatsteConfigWijziging() {
        return tijdLaatsteConfigWijziging;
    }

    /**
     * Sets the value of the tijdLaatsteConfigWijziging property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdLaatsteConfigWijziging(XMLGregorianCalendar value) {
        this.tijdLaatsteConfigWijziging = value;
    }

    /**
     * Gets the value of the meetpunt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meetpunt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeetpunt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Miv.Meetpunt }
     * 
     * 
     */
    public List<Miv.Meetpunt> getMeetpunt() {
        if (meetpunt == null) {
            meetpunt = new ArrayList<Miv.Meetpunt>();
        }
        return this.meetpunt;
    }

    /**
     * Gets the value of the schemaVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * Sets the value of the schemaVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchemaVersion(String value) {
        this.schemaVersion = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="lve_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="tijd_waarneming" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="tijd_laatst_gewijzigd" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="actueel_publicatie" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="beschikbaar" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="defect" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="geldig" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="3"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="meetdata" maxOccurs="5" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="verkeersintensiteit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="voertuigsnelheid_rekenkundig" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="voertuigsnelheid_harmonisch" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="klasse_id" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                       &lt;enumeration value="1"/>
     *                       &lt;enumeration value="2"/>
     *                       &lt;enumeration value="3"/>
     *                       &lt;enumeration value="4"/>
     *                       &lt;enumeration value="5"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="rekendata" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="bezettingsgraad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="beschikbaarheidsgraad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="onrustigheid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="unieke_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="beschrijvende_id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "lveNr",
        "tijdWaarneming",
        "tijdLaatstGewijzigd",
        "actueelPublicatie",
        "beschikbaar",
        "defect",
        "geldig",
        "meetdata",
        "rekendata"
    })
    public static class Meetpunt {

        @XmlElement(name = "lve_nr")
        protected BigInteger lveNr;
        @XmlElement(name = "tijd_waarneming")
        protected XMLGregorianCalendar tijdWaarneming;
        @XmlElement(name = "tijd_laatst_gewijzigd")
        protected XMLGregorianCalendar tijdLaatstGewijzigd;
        @XmlElement(name = "actueel_publicatie")
        protected Integer actueelPublicatie;
        protected Integer beschikbaar;
        protected Integer defect;
        protected Integer geldig;
        protected List<Miv.Meetpunt.Meetdata> meetdata;
        protected Miv.Meetpunt.Rekendata rekendata;
        @XmlAttribute(name = "unieke_id", required = true)
        protected String uniekeId;
        @XmlAttribute(name = "beschrijvende_id")
        protected String beschrijvendeId;

        /**
         * Gets the value of the lveNr property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLveNr() {
            return lveNr;
        }

        /**
         * Sets the value of the lveNr property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLveNr(BigInteger value) {
            this.lveNr = value;
        }

        /**
         * Gets the value of the tijdWaarneming property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTijdWaarneming() {
            return tijdWaarneming;
        }

        /**
         * Sets the value of the tijdWaarneming property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTijdWaarneming(XMLGregorianCalendar value) {
            this.tijdWaarneming = value;
        }

        /**
         * Gets the value of the tijdLaatstGewijzigd property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTijdLaatstGewijzigd() {
            return tijdLaatstGewijzigd;
        }

        /**
         * Sets the value of the tijdLaatstGewijzigd property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTijdLaatstGewijzigd(XMLGregorianCalendar value) {
            this.tijdLaatstGewijzigd = value;
        }

        /**
         * Gets the value of the actueelPublicatie property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getActueelPublicatie() {
            return actueelPublicatie;
        }

        /**
         * Sets the value of the actueelPublicatie property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setActueelPublicatie(Integer value) {
            this.actueelPublicatie = value;
        }

        /**
         * Gets the value of the beschikbaar property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getBeschikbaar() {
            return beschikbaar;
        }

        /**
         * Sets the value of the beschikbaar property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setBeschikbaar(Integer value) {
            this.beschikbaar = value;
        }

        /**
         * Gets the value of the defect property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getDefect() {
            return defect;
        }

        /**
         * Sets the value of the defect property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setDefect(Integer value) {
            this.defect = value;
        }

        /**
         * Gets the value of the geldig property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getGeldig() {
            return geldig;
        }

        /**
         * Sets the value of the geldig property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setGeldig(Integer value) {
            this.geldig = value;
        }

        /**
         * Gets the value of the meetdata property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the meetdata property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMeetdata().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Miv.Meetpunt.Meetdata }
         * 
         * 
         */
        public List<Miv.Meetpunt.Meetdata> getMeetdata() {
            if (meetdata == null) {
                meetdata = new ArrayList<Miv.Meetpunt.Meetdata>();
            }
            return this.meetdata;
        }

        /**
         * Gets the value of the rekendata property.
         * 
         * @return
         *     possible object is
         *     {@link Miv.Meetpunt.Rekendata }
         *     
         */
        public Miv.Meetpunt.Rekendata getRekendata() {
            return rekendata;
        }

        /**
         * Sets the value of the rekendata property.
         * 
         * @param value
         *     allowed object is
         *     {@link Miv.Meetpunt.Rekendata }
         *     
         */
        public void setRekendata(Miv.Meetpunt.Rekendata value) {
            this.rekendata = value;
        }

        /**
         * Gets the value of the uniekeId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUniekeId() {
            return uniekeId;
        }

        /**
         * Sets the value of the uniekeId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUniekeId(String value) {
            this.uniekeId = value;
        }

        /**
         * Gets the value of the beschrijvendeId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBeschrijvendeId() {
            return beschrijvendeId;
        }

        /**
         * Sets the value of the beschrijvendeId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBeschrijvendeId(String value) {
            this.beschrijvendeId = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="verkeersintensiteit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="voertuigsnelheid_rekenkundig" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="voertuigsnelheid_harmonisch" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="klasse_id" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *             &lt;enumeration value="1"/>
         *             &lt;enumeration value="2"/>
         *             &lt;enumeration value="3"/>
         *             &lt;enumeration value="4"/>
         *             &lt;enumeration value="5"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "verkeersintensiteit",
            "voertuigsnelheidRekenkundig",
            "voertuigsnelheidHarmonisch"
        })
        public static class Meetdata {

            protected Integer verkeersintensiteit;
            @XmlElement(name = "voertuigsnelheid_rekenkundig")
            protected Integer voertuigsnelheidRekenkundig;
            @XmlElement(name = "voertuigsnelheid_harmonisch")
            protected Integer voertuigsnelheidHarmonisch;
            @XmlAttribute(name = "klasse_id", required = true)
            protected int klasseId;

            /**
             * Gets the value of the verkeersintensiteit property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getVerkeersintensiteit() {
                return verkeersintensiteit;
            }

            /**
             * Sets the value of the verkeersintensiteit property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setVerkeersintensiteit(Integer value) {
                this.verkeersintensiteit = value;
            }

            /**
             * Gets the value of the voertuigsnelheidRekenkundig property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getVoertuigsnelheidRekenkundig() {
                return voertuigsnelheidRekenkundig;
            }

            /**
             * Sets the value of the voertuigsnelheidRekenkundig property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setVoertuigsnelheidRekenkundig(Integer value) {
                this.voertuigsnelheidRekenkundig = value;
            }

            /**
             * Gets the value of the voertuigsnelheidHarmonisch property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getVoertuigsnelheidHarmonisch() {
                return voertuigsnelheidHarmonisch;
            }

            /**
             * Sets the value of the voertuigsnelheidHarmonisch property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setVoertuigsnelheidHarmonisch(Integer value) {
                this.voertuigsnelheidHarmonisch = value;
            }

            /**
             * Gets the value of the klasseId property.
             * 
             */
            public int getKlasseId() {
                return klasseId;
            }

            /**
             * Sets the value of the klasseId property.
             * 
             */
            public void setKlasseId(int value) {
                this.klasseId = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="bezettingsgraad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="beschikbaarheidsgraad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="onrustigheid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "bezettingsgraad",
            "beschikbaarheidsgraad",
            "onrustigheid"
        })
        public static class Rekendata {

            protected Integer bezettingsgraad;
            protected Integer beschikbaarheidsgraad;
            protected Integer onrustigheid;

            /**
             * Gets the value of the bezettingsgraad property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getBezettingsgraad() {
                return bezettingsgraad;
            }

            /**
             * Sets the value of the bezettingsgraad property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setBezettingsgraad(Integer value) {
                this.bezettingsgraad = value;
            }

            /**
             * Gets the value of the beschikbaarheidsgraad property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getBeschikbaarheidsgraad() {
                return beschikbaarheidsgraad;
            }

            /**
             * Sets the value of the beschikbaarheidsgraad property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setBeschikbaarheidsgraad(Integer value) {
                this.beschikbaarheidsgraad = value;
            }

            /**
             * Gets the value of the onrustigheid property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getOnrustigheid() {
                return onrustigheid;
            }

            /**
             * Sets the value of the onrustigheid property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setOnrustigheid(Integer value) {
                this.onrustigheid = value;
            }

        }

    }

}
