<?php

namespace App\Entity;

use App\Repository\VisiteRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: VisiteRepository::class)]
class Visite
{
   
   
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column()]
    private ?int $idvisite = null;
    /**
      *  @Assert\NotBlank(message="nom de visiteur est obligatoire")
     * @Assert\NotBlank(message="NB: nom bisiteur  doit etre Obligatoire et min caractere 2 et max 99")
     * @Assert\Length(
     *      min = 2,
     *      max = 100,
     *      minMessage = "doit etre >=4 ",
     *      maxMessage = "doit etre <=100" )
     * @Groups("visite")
     */


    #[ORM\Column(length: 255)]
    private ?string $nomvisiteur = null;
    /**
      *  @Assert\NotBlank(message="message de patient est obligatoire")
     * @Assert\NotBlank(message="NB: nom patient  doit etre Obligatoire et min caractere 2 et max 99")
     * @Assert\Length(
     *      min = 2,
     *      max = 100,
     *      minMessage = "doit etre >=4 ",
     *      maxMessage = "doit etre <=100" )
     *  @Groups("visite")
     */


    #[ORM\Column(length: 255)]
    private ?string $nompatient = null;
    /**
      *  @Assert\NotBlank(message="date de visite est obligatoire")
      * @Groups("visite")
      */

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $date = null;
    /**
      *  @Assert\NotBlank(message="numero de visiteur est obligatoire")
     * @Assert\NotBlank(message="NB: numero de visiteur doit etre Obligatoire et min 8 et max 12")
     * @Assert\Length(
     *      min = 8,
     *      max =15,
     *      minMessage = "doit etre >=8 ",
     *      maxMessage = "doit etre <=13" )
     *  @Groups("visite")
     */

    #[ORM\Column]
    private ?int $numerotele = null;
      /**
      *  @Assert\NotBlank(message="message de visiteur est obligatoire")
      * @Groups("visite")
     * 
     */

    #[ORM\Column(type: Types::TEXT)]
    private ?string $message = null;

    #[ORM\ManyToOne(inversedBy: 'visites')]
    private ?Patient $patient = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $image = null;

    public function getIdvisite(): ?int
    {
        return $this->idvisite;
    }

   
   
    public function setIdvisite(int $idvisite): self
    {
        $this->getidvisite = $idvisite;

        return $this;
    }

    public function getNomvisiteur(): ?string
    {
        return $this->nomvisiteur;
    }

    public function setNomvisiteur(string $nomvisiteur): self
    {
        $this->nomvisiteur = $nomvisiteur;

        return $this;
    }

    public function getNompatient(): ?string
    {
        return $this->nompatient;
    }

    public function setNompatient(string $nompatient): self
    {
        $this->nompatient = $nompatient;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getNumerotele(): ?int
    {
        return $this->numerotele;
    }

    public function setNumerotele(int $numerotele): self
    {
        $this->numerotele = $numerotele;

        return $this;
    }

    public function getMessage(): ?string
    {
        return $this->message;
    }

    public function setMessage(string $message): self
    {
        $this->message = $message;

        return $this;
    }

    public function getPatient(): ?Patient
    {
        return $this->patient;
    }

    public function setPatient(?Patient $patient): self
    {
        $this->patient = $patient;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(?string $image): self
    {
        $this->image = $image;

        return $this;
    }
}
