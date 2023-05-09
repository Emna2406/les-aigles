<?php

namespace App\Entity;

use App\Repository\OrdonanceRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: OrdonanceRepository::class)]
class Ordonance
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    /** 
    *@Groups("Ordonance")
    */
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    /** 
    *@Groups("Ordonance")
    */
    private ?\DateTimeInterface $date = null;

    #[ORM\Column(length: 255)]
    /**
     * @Assert\NotBlank(message="NB: description  doit etre Obligatoire et min caractere 7 et max 99")
     * @Assert\Length(
     *      min = 7,
     *      max = 100,
     *      minMessage = "doit etre >=4 ",
     *      maxMessage = "doit etre <=100" )
     */
    /** 
    *@Groups("Ordonance")
    */
    private ?string $description = null;

    #[ORM\Column(length: 255)]
     /**
     * @Assert\NotBlank(message=" NB:L'image d'odonnance doit etre obligatoire")
     */
    /** 
    *@Groups("Ordonance")
    */
    private ?string $image = null;

    #[ORM\ManyToOne(inversedBy: 'ordonance')]
    /** 
    *@Groups("Ordonance")
    */
    private ?Consultation $consultation = null;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

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

    public function getConsultation(): ?Consultation
    {
        return $this->consultation;
    }

    public function setConsultation(?Consultation $consultation): self
    {
        $this->consultation = $consultation;

        return $this;
    }
}
