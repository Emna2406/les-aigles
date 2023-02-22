<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230213023501 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE certificat DROP FOREIGN KEY certificat_ibfk_1');
        $this->addSql('ALTER TABLE condidat DROP FOREIGN KEY condidat_ibfk_1');
        $this->addSql('ALTER TABLE consultation DROP FOREIGN KEY consultation_ibfk_1');
        $this->addSql('ALTER TABLE consultation DROP FOREIGN KEY consultation_ibfk_2');
        $this->addSql('DROP TABLE certificat');
        $this->addSql('DROP TABLE condidat');
        $this->addSql('DROP TABLE consultation');
        $this->addSql('DROP TABLE offre');
        $this->addSql('DROP TABLE ordonnance');
        $this->addSql('DROP TABLE partenaire');
        $this->addSql('DROP TABLE produit');
        $this->addSql('DROP TABLE rdv');
        $this->addSql('DROP TABLE type');
        $this->addSql('DROP TABLE user');
        $this->addSql('DROP TABLE visite');
        $this->addSql('ALTER TABLE messenger_messages CHANGE delivered_at delivered_at DATETIME DEFAULT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE certificat (idcerti INT AUTO_INCREMENT NOT NULL, iduser INT NOT NULL, date_debut DATE NOT NULL, date_expiration DATE NOT NULL, image TEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, INDEX iduser (iduser), PRIMARY KEY(idcerti)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE condidat (id_cond INT AUTO_INCREMENT NOT NULL, id_offre INT NOT NULL, nom VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, prenom VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, cv TEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, email VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, INDEX id_offre (id_offre), PRIMARY KEY(id_cond)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE consultation (idcon INT AUTO_INCREMENT NOT NULL, idcertif INT NOT NULL, idord INT NOT NULL, INDEX idcertif (idcertif), INDEX idord (idord), PRIMARY KEY(idcon)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE offre (id_offre INT AUTO_INCREMENT NOT NULL, description TEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, id_service INT NOT NULL, INDEX id_service (id_service), PRIMARY KEY(id_offre)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE ordonnance (idord INT AUTO_INCREMENT NOT NULL, date DATE NOT NULL, description TEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, image TEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, iduser INT NOT NULL, INDEX iduser (iduser), PRIMARY KEY(idord)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE partenaire (id_part INT AUTO_INCREMENT NOT NULL, nom VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, email TEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(id_part)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE produit (idproduit INT AUTO_INCREMENT NOT NULL, libelle VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, stock INT NOT NULL, id_part INT NOT NULL, INDEX id_part (id_part), PRIMARY KEY(idproduit)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE rdv (idrv INT AUTO_INCREMENT NOT NULL, nompatient VARCHAR(30) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, date DATETIME NOT NULL, numtel INT NOT NULL, message TEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, iduser INT NOT NULL, INDEX iduser (iduser), PRIMARY KEY(idrv)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE type (idtype INT AUTO_INCREMENT NOT NULL, lib VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(idtype)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE user (iduser INT AUTO_INCREMENT NOT NULL, nom VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, prenom VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, email VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, idtype INT NOT NULL, INDEX idtype (idtype), PRIMARY KEY(iduser)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE visite (idvisite INT AUTO_INCREMENT NOT NULL, date DATETIME NOT NULL, numtele INT NOT NULL, message TEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, iduser INT NOT NULL, INDEX iduser (iduser), PRIMARY KEY(idvisite)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE certificat ADD CONSTRAINT certificat_ibfk_1 FOREIGN KEY (iduser) REFERENCES user (iduser) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE condidat ADD CONSTRAINT condidat_ibfk_1 FOREIGN KEY (id_offre) REFERENCES offre (id_offre) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE consultation ADD CONSTRAINT consultation_ibfk_1 FOREIGN KEY (idcertif) REFERENCES certificat (idcerti) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE consultation ADD CONSTRAINT consultation_ibfk_2 FOREIGN KEY (idord) REFERENCES ordonnance (idord) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE messenger_messages CHANGE delivered_at delivered_at DATETIME DEFAULT \'NULL\'');
    }
}
