<?php

namespace App\Entity;

use App\Repository\MedecinRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: MedecinRepository::class)]
class Medecin
{
    
   
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $nommedecin = null;

  

    public function getIdmedecin(): ?int
    {
        return $this->id;
    }

   

    public function getNommedecin(): ?string
    {
        return $this->nommedecin;
    }

    public function setNommedecin(string $nommedecin): self
    {
        $this->nommedecin = $nommedecin;

        return $this;
    }
}
