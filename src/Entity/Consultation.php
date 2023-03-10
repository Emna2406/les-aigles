<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Consultation
 *
 * @ORM\Table(name="consultation", indexes={@ORM\Index(name="idord", columns={"idord"}), @ORM\Index(name="idcertif", columns={"idcertif"})})
 * @ORM\Entity
 */
class Consultation
{
    /**
     * @var int
     *
     * @ORM\Column(name="idcon", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idcon;

    /**
     * @var \Ordonnance
     *
     * @ORM\ManyToOne(targetEntity="Ordonnance")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idord", referencedColumnName="idord")
     * })
     */
    private $idord;

    /**
     * @var \Certificat
     *
     * @ORM\ManyToOne(targetEntity="Certificat")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idcertif", referencedColumnName="idcerti")
     * })
     */
    private $idcertif;


}
