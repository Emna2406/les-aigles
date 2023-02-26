<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\ResetType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $user, array $options): void
    {
        $user
            ->add('nom')
            ->add('prenom')
            ->add('email')
            ->add('username')
            ->add('pass', PasswordType::class)
            ->add('ajouter',SubmitType::class)
            ->add('Reset',ResetType::class)


        ;
    }


}
