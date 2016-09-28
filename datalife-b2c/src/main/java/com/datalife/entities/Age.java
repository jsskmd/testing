package com.datalife.entities;

/**
 * Created by Dipak on 1/24/2015.
 */
public class Age {

        private int days;
        private int months;
        private int years;

        private Age()
        {
            //Prevent default constructor
        }

    public Age(int years)
    {
        this.years = years;
    }

        public int getDays()
        {
            return this.days;
        }

        public int getMonths()
        {
            return this.months;
        }

        public int getYears()
        {
            return this.years;
        }

        @Override
        public String toString()
        {
            return years + " Years";
        }
    }

