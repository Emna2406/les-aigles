<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Visite
 *
 * @ORM\Table(name="visite", indexes={@ORM\Index(name="iduser", columns={"iduser"})})
 * @ORM\Entity
 */
class Visite
{
    /**
     * @var int
     *
     * @ORM\Column(name="idvisite", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idvisite;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date", type="datetime", nullable=false)
     */
    private $date;

    /**
     * @var int
     *
     * @ORM\Column(name="numtele", type="integer", nullable=false)
     */
    private $numtele;

    /**
     * @var string
     *
     * @ORM\Column(name="message", type="text", length=65535, nullable=false)
     */
    private $message;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="iduser", referencedColumnName="iduser")
     * })
     */
    private $iduser;


}
