<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;

/**
 * Type
 *
 * @ORM\Table(name="type")
 * @ORM\Entity
 */
class Type
{
    /**
     * @var int
     *
     * @ORM\Column(name="idtype", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idtype;

    /**
     * @var string
     *
     * @ORM\Column(name="lib", type="string", length=50, nullable=false)
     */
    private $lib;








    /**
     * @return int
     */
    public function getIdtype(): int
    {
            return $this->idtype;
    }

    /**
     * @param int $idtype
     */
    public function setIdtype(int $idtype): void
    {
        $this->idtype = $idtype;
    }

    /**
     * @return string
     */
    public function getLib(): string
    {
        return $this->lib;
    }

    /**
     * @param string $lib
     */
    public function setLib(string $lib): void
    {
        $this->lib = $lib;
    }




}
