<?php

namespace App\Entity;

use App\Repository\ConsultationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: ConsultationRepository::class)]
class Consultation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $datedeconsultation = null;

    #[ORM\Column(length: 255)]
    /**
     * @Assert\NotBlank(message=" *NB:l'image de consultation est obligatoire")
     */
    private ?string $image = null;

    #[ORM\ManyToOne(inversedBy: 'consultations')]
    private ?Patient $patient = null;

    #[ORM\ManyToOne(inversedBy: 'consultations')]
    private ?Medecin $medecin = null;

    #[ORM\OneToMany(mappedBy: 'consultation', targetEntity: Ordonance::class)]
    private Collection $ordonance;

    public function __construct()
    {
        $this->ordonance = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDatedeconsultation(): ?\DateTimeInterface
    {
        return $this->datedeconsultation;
    }

    public function setDatedeconsultation(\DateTimeInterface $datedeconsultation): self
    {
        $this->datedeconsultation = $datedeconsultation;

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

    /**
     * @return Collection<int, Ordonance>
     */
    public function getOrdonance(): Collection
    {
        return $this->ordonance;
    }

    public function addOrdonance(Ordonance $ordonance): self
    {
        if (!$this->ordonance->contains($ordonance)) {
            $this->ordonance->add($ordonance);
            $ordonance->setConsultation($this);
        }

        return $this;
    }

    public function removeOrdonance(Ordonance $ordonance): self
    {
        if ($this->ordonance->removeElement($ordonance)) {
            // set the owning side to null (unless already changed)
            if ($ordonance->getConsultation() === $this) {
                $ordonance->setConsultation(null);
            }
        }

        return $this;
    }
}
