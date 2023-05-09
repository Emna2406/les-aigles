<?php

namespace App\Entity;

use App\Repository\CertificatRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: CertificatRepository::class)]
class Certificat
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    
    /** 
    *@Groups("Certificat")
    */
    private ?int $id = null;
    

    #[ORM\ManyToOne(inversedBy: 'certificats')]
    /** 
    *@Groups("Certificat")
    */
    private ?Patient $patient = null;

    #[ORM\ManyToOne(inversedBy: 'certificats')]
    /** 
    *@Groups("Certificat")
    */
    private ?Medecin $medecin = null;
  
    #[ORM\Column(type: Types::DATE_MUTABLE)]
    /** 
    *@Groups("Certificat")
    */
    private ?\DateTimeInterface $Datedeb = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    /** 
    *@Groups("Certificat")
    */
    private ?\DateTimeInterface $datefin = null;

    #[ORM\Column(length: 255)]
    /** 
    *@Groups("Certificat")
    */
    private ?string $image = null;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getMedecin(): ?Medecin
    {
        return $this->medecin;
    }

    public function setMedecin(?Medecin $medecin): self
    {
        $this->medecin = $medecin;

        return $this;
    }

    public function getDatedeb(): ?\DateTimeInterface
    {
        return $this->Datedeb;
    }

    public function setDatedeb(\DateTimeInterface $Datedeb): self
    {
        $this->Datedeb = $Datedeb;

        return $this;
    }

    public function getDatefin(): ?\DateTimeInterface
    {
        return $this->datefin;
    }

    public function setDatefin(\DateTimeInterface $datefin): self
    {
        $this->datefin = $datefin;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }
}