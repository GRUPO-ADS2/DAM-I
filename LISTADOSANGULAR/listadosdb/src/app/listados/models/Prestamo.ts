import { Solicitud } from "./Solicitud"

export class Prestamo {
    idPrestamo!:number;
    fechaPrestamo?: Date;
    estado!:string;
    fechaDevReal?:Date;
    solicitud!:Solicitud;
}