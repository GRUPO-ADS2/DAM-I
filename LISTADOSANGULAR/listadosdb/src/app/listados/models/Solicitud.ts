import { Alumno } from "./Alumno";
import { Material } from "./Material";

export class Solicitud{
    idSolicitud!:number;
    cantidad!:number;
    estado!:string;
    material!:Material;
    alumno!:Alumno;
}