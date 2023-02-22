<?php

namespace App\Entity;

use App\Repository\PatientRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: PatientRepository::class)]
class Patient
{
   
   
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $nomprenompatient = null;

    #[ORM\OneToMany(mappedBy: 'patient', targetEntity: Rdv::class)]
    private Collection $rdvs;

    #[ORM\OneToMany(mappedBy: 'patient', targetEntity: Visite::class)]
    private Collection $visites;

    public function __construct()
    {
        $this->rdvs = new ArrayCollection();
        $this->visites = new ArrayCollection();
    }

   

    public function getIdpatient(): ?int
    {
        return $this->id;
    }

    

    public function getNomprenompatient(): ?string
    {
        return $this->nomprenompatient;
    }

    public function setNomprenompatient(string $nomprenompatient): self
    {
        $this->nomprenompatient = $nomprenompatient;

        return $this;
    }

    /**
     * @return Collection<int, Rdv>
     */
    public function getRdvs(): Collection
    {
        return $this->rdvs;
    }

    public function addRdv(Rdv $rdv): self
    {
        if (!$this->rdvs->contains($rdv)) {
            $this->rdvs->add($rdv);
            $rdv->setPatient($this);
        }

        return $this;
    }

    public function removeRdv(Rdv $rdv): self
    {
        if ($this->rdvs->removeElement($rdv)) {
            // set the owning side to null (unless already changed)
            if ($rdv->getPatient() === $this) {
                $rdv->setPatient(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Visite>
     */
    public function getVisites(): Collection
    {
        return $this->visites;
    }

    public function addVisite(Visite $visite): self
    {
        if (!$this->visites->contains($visite)) {
            $this->visites->add($visite);
            $visite->setPatient($this);
        }

        return $this;
    }

    public function removeVisite(Visite $visite): self
    {
        if ($this->visites->removeElement($visite)) {
            // set the owning side to null (unless already changed)
            if ($visite->getPatient() === $this) {
                $visite->setPatient(null);
            }
        }

        return $this;
    }
}
