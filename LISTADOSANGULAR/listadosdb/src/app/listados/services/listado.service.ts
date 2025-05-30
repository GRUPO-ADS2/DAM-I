import { Injectable } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Solicitud } from '../models/Solicitud';
import { Prestamo } from '../models/Prestamo';
import { Penalizacion } from '../models/Penalizacion';
@Injectable({
  providedIn: 'root'
})
export class ListadoService {
  private urlBase:string = 'https://prestamo-daw-i-7cqp.onrender.com';
  constructor(private http : HttpClient) { }
  
  listarSolicitudes(): Observable<Solicitud[]>{
    return this.http.get<Solicitud[]>(this.urlBase+"/solicitudes");
  }
  listarPrestamos(): Observable<Prestamo[]>{
    return this.http.get<Prestamo[]>(this.urlBase+"/prestamos");
  }
  listarPenalizacion(): Observable<Penalizacion[]>{
    return this.http.get<Penalizacion[]>(this.urlBase+ "/penalizaciones");
  }
}
