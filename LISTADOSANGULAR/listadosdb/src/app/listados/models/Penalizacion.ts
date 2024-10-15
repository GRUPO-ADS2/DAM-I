import { Prestamo } from "./Prestamo";

export class Penalizacion {
    idPenalizacion!:number;
    fechaPenalizacion!:Date;
    descripcion!:string;
    prestamo!:Prestamo;
}