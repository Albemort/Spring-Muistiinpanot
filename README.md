# Spring Boot Muistiinpanot -sovelluksen Dokumentaatio

## 1. Johdanto

Tämä dokumentti kuvaa Spring Boot -sovellusta, joka on suunniteltu tapahtumien ja kategorioiden hallintaan. Sovellus tarjoaa käyttäjille mahdollisuuden luoda, tarkastella, päivittää ja poistaa tapahtumia, joita voidaan liittää erilaisiin kategorioihin.

## 2. Tietokantamalli

### 2.1 Kategoria-entiteetti

Kategoria-entiteetti kuvaa tapahtumien kategorioita tietokannassa. Jokaisella kategorialla on ainutlaatuinen nimi, ja ne voivat liittyä moneen tapahtumaan. Kategoria-entiteetti määritellään JPA-annotaatioilla (`@Entity, @Table, @Column`) ja se tallentaa kategorioiden tiedot tietokantaan. Kategorioilla on yhteys tapahtumiin `@ManyToMany`-relaation avulla.

### 2.2 Tapahtuma-entiteetti

Tapahtuma-entiteetti tallentaa yksittäisten tapahtumien tiedot tietokantaan. Jokaisella tapahtumalla voi olla useita kategorioita, mikä on toteutettu `@ManyToMany`-relaation avulla.

## 3. Repositoriot

### 3.1 CategoryRepository

`CategoryRepository` tarjoaa pääsyn kategoria-entiteettien tietokantaan. Siinä on erityinen metodi, `findByCategoryName`, joka mahdollistaa kategorian hakemisen sen nimen perusteella.

### 3.2 EventRepository

`EventRepository` tarjoaa pääsyn tapahtuma-entiteettien tietokantaan.

## 4. Kontrollerit

### 4.1 CategoryController

`CategoryController` hallinnoi kategorioihin liittyviä HTTP-pyyntöjä. Se tarjoaa reittejä kategorioiden luomiseen ja näyttämiseen käyttöliittymässä.

### 4.2 EventController

`EventController` vastaa tapahtumien hallinnasta. Se tarjoaa reitit tapahtumien luomiseen, päivittämiseen, poistamiseen ja näyttämiseen.

## 5. RESTful-rajapinta

Sovellus tarjoaa RESTful-rajapinnan HTTP-pyyntöjen kautta, mikä mahdollistaa helpon vuorovaikutuksen tapahtumien ja kategorioiden kanssa. RESTful-rajapinnan polut on suunniteltu noudattamaan REST-arkkitehtuurin periaatteita.

## 6. Tietokannan Alustaminen

`DatabaseInitializer` on komponentti, joka käynnistyy sovelluksen käynnistyessä. Sen tehtävänä on tarkistaa, ovatko kategoriat jo tietokannassa, ja lisätä oletuskategoriat, mikäli niitä ei vielä ole.

## 7. Verkkokonfiguraatio

`WebConfig` sisältää konfiguraation piilotettujen HTTP-metodien käsittelyyn. Tämä mahdollistaa esimerkiksi DELETE- ja PUT-pyyntöjen käytön tavallisten lomakkeiden sijaan.

## 8. Yhteenveto

Sovellus tarjoaa selkeän ja joustavan tavan hallita tapahtumia ja kategorioita. Kategorioiden ja tapahtumien tietomallit sekä niihin liittyvät kontrollerit ja repositoryt muodostavat vahvan perustan järjestelmän toiminnalle. Tietokannan alustaminen varmistaa, että sovelluksessa on aina oletuskategoriat saatavilla, ja verkkokonfiguraatio tekee mahdolliseksi monipuolisen HTTP-metodien käytön.
