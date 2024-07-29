import { role } from "./role";

export class getuser{
    id!:number;
    email!:string;
    password!:string;
    nom!:string;
    prenom!:string;
    societe!:string;
    roles!:role[];
    equipes!:string[];
      
}