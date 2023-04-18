package com.banquito.banquitoApp.utils.operaciones;

    public enum CalificacionRiesgo {
        AAA, AA, A, BBB, BB, B, C, D, F;
        public boolean isRisky(){
            switch (this){
                case AAA, AA, A, BBB, BB, B -> {
                    return false;
                }
                default -> {
                    return true;
                }

            }
        }
    }

