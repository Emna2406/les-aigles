<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;

use Doctrine\ORM\Mapping as ORM;

/**
 * User
 *
 * @ORM\Table(name="user", indexes={@ORM\Index(name="idtype", columns={"idtype"})})
 * @ORM\Entity
 */
class User
{
    /**
     * @var int
     *
     * @ORM\Column(name="iduser", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $iduser;

    /**
     * @Assert\NotBlank(message=" nom doit etre non vide")
     * @Assert\Length(
     *      min = 5,
     *      minMessage=" Entrer un titre au mininum de 4 caracteres"
     *     )
     * @ORM\Column(name="nom", type="string", length=50, nullable=false)
     */
    private $nom;

    /**
     * @Assert\NotBlank(message=" Prenom doit etre non vide")
     * @Assert\Length(
     *      min = 5,
     *      minMessage=" Entrer un titre au mininum de 4 caracteres"
     *     )
     * @ORM\Column(name="prenom", type="string", length=50, nullable=false)
     */
    private $prenom;

    /**
     * @Assert\NotBlank(message="Mail invalid")
     * @ORM\Column(name="email", type="string", length=50, nullable=false)
     */
    private $email;

    /**
     * @Assert\NotBlank(message=" username doit etre non vide")
     * @Assert\Length(
     *      min = 5,
     *      minMessage=" Entrer un username au mininum de 4 caracteres"
     *     )
     * @ORM\Column(name="username", type="string", length=50, nullable=false)
     */
    private $username;

    /**
     * @Assert\NotBlank(message=" Entrer un password au mininum de 8 caracteres ")
     * @Assert\Length(
     *      min = 8,
     *      minMessage=" Entrer un password au mininum de 8 caracteres"
     *     )
     * @ORM\Column(name="password", type="string", length=50, nullable=false)
     */
    private $pass;

    /**
     * @var \Type
     *
     * @ORM\ManyToOne(targetEntity="Type")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idtype", referencedColumnName="idtype")
     * })
     */
    private $idtype;


    /**
     * @return string
     */
    public function getUsername(): string
    {
        return $this->username;
    }

    /**
     * @param string $username
     */
    public function setUsername(string $username): void
    {
        $this->username = $username;
    }

    /**
     * @return string
     */
    public function getPass(): string
    {
        return $this->pass;
    }

    /**
     * @param string $pass
     */
    public function setPass(string $pass): void
    {
        $this->pass = $pass;
    }







    /**
     * @return int
     */
    public function getIduser(): int
    {
        return $this->iduser;
    }

    /**
     * @param int $iduser
     */
    public function setIduser(int $iduser): void
    {
        $this->iduser = $iduser;
    }

    /**
     * @return string
     */
    public function getNom(): string
    {
        return $this->nom;
    }

    /**
     * @param string $nom
     */
    public function setNom(string $nom): void
    {
        $this->nom = $nom;
    }

    /**
     * @return string
     */
    public function getPrenom(): string
    {
        return $this->prenom;
    }

    /**
     * @param string $prenom
     */
    public function setPrenom(string $prenom): void
    {
        $this->prenom = $prenom;
    }

    /**
     * @return string
     */
    public function getEmail(): string
    {
        return $this->email;
    }

    /**
     * @param string $email
     */
    public function setEmail(string $email): void
    {
        $this->email = $email;
    }




}
