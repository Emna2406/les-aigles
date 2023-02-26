<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class Types extends AbstractType
{
    public function buildForm(FormBuilderInterface $type, array $options): void
    {
        $type
            ->add('lib')

            ->add('ajouter',SubmitType::class)
        ;
    }


}
