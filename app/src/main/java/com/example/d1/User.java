package com.example.d1;

public class User {

        private String firstName;
        // private String lastName;
        private String email;
        private String password;
        private String number, emergCnt, emergCntNum, medNote;

        public User(){}
        public User(String firstName,String email,String password){
            this.firstName = firstName;
            //this.lastName = lastName;
            //this.userName = userName;
            this.email = email;
            this.password = password;
            this.number = "Edit your profile!";
            this.emergCnt = "Edit your profile!";
            this.emergCntNum = "Edit your profile!";
            this.medNote = "Edit your profile!";
        }


       public User(String firstName,String email,String password, String number, String emergCnt, String emergCntNum, String medNote){
           this.firstName = firstName;
           //this.lastName = lastName;
           //this.userName = userName;
           this.email = email;
           this.password = password;
           this.number = number;
           this.emergCnt = emergCnt;
           this.emergCntNum = emergCntNum;
           this.medNote = medNote;
       }

    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    // public String getLastName(){return lastName;}
    // public void setLastName(String lastName){this.lastName = lastName;}

    // public String getUserName(){return userName;}
    //public void setUserName(String userName){this.userName = userName;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    public String getNumber(){return number;}
    public void setNumber(String number){this.number = number;}

    public String getEmergCnt(){return emergCnt;}
    public void setEmergCnt(String emergCnt){this.emergCnt = emergCnt;}

    public String getEmergCntNum(){return emergCntNum;}
    public void setEmergCntNum(String emergCntNum){this.emergCntNum = emergCntNum;}

    public String getMedNote(){return medNote;}
    public void setMedNote(String medNote){this.medNote = medNote;}
}
