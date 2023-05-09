<?php

namespace App\Entity;

use App\Repository\ChambreRepository;
use Doctrine\ORM\Mapping as ORM;
use http\Message;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: ChambreRepository::class)]
class Chambre
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    /**
     *  @Groups("Chambre")
     */
    private ?int $id = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message=" NB:NUM  doit etre Obligatoire")
     * @Assert\Positive(message=" NB:le num doit etre positif")
     *  @Groups("Chambre")
     */
    private ?String $num = null;
    #[ORM\Column]
    /**
     * @Assert\NotBlank(message=" NB: description  doit etre Obligatoire ")
     * @Assert\Positive(message=" le num doit etre positif")
     * @Assert\Length(
     *      min = 1,
     *      max = 7,
     *      minMessage = "doit etre >=1 ",
     *      maxMessage = "doit etre <=7" )
     *  @Groups("Chambre")
     */
    private ?String $capacite = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message="NB: PRIX  doit etre Obligatoire")
     * @Assert\Positive(message="NB: PRIX doit etre positif")
     *  @Groups("Chambre")
     */
    private ?String $prix = null;

    #[ORM\Column(length: 255)]
    /**
     *  @Groups("Chambre")
     */
    private ?string $etat = null;

    #[ORM\ManyToOne(inversedBy: 'chambres')]
    private ?Service $service = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNum(): ?String
    {
        return $this->num;
    }

    public function setNum(String $num): self
    {
        $this->num = $num;

        return $this;
    }

    public function getCapacite(): ?String
    {
        return $this->capacite;
    }

    public function setCapacite(String $capacite): self
    {
        $this->capacite = $capacite;

        return $this;
    }

    public function getPrix(): ?String
    {
        return $this->prix;
    }

    public function setPrix(String $prix): self
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
