<?php

namespace App\Entity;

use App\Repository\ChambreRepository;
use Doctrine\ORM\Mapping as ORM;
use http\Message;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: ChambreRepository::class)]
class Chambre
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message=" NB:NUM  doit etre Obligatoire")
     * @Assert\Positive(message=" NB:le num doit etre positif")
     */
    private ?int $num = null;
    #[ORM\Column]
    /**
     * @Assert\NotBlank(message=" NB: description  doit etre Obligatoire ")
     * @Assert\Positive(message=" le num doit etre positif")
     */
    private ?int $capacite = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message="NB: PRIX  doit etre Obligatoire")
     * @Assert\Positive(message="NB: PRIX doit etre positif")
     */
    private ?float $prix = null;

    #[ORM\Column(length: 255)]
    /**
     * @Assert\NotBlank(message="NB: Etat  doit etre Obligatoire")
     */

    private ?string $etat = null;

    #[ORM\ManyToOne(inversedBy: 'chambres')]
    private ?Service $service = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNum(): ?int
    {
        return $this->num;
    }

    public function setNum(int $num): self
    {
        $this->num = $num;

        return $this;
    }

    public function getCapacite(): ?int
    {
        return $this->capacite;
    }

    public function setCapacite(int $capacite): self
    {
        $this->capacite = $capacite;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getService(): ?Service
    {
        return $this->service;
    }

    public function setService(?Service $service): self
    {
        $this->service = $service;

        return $this;
    }
}
