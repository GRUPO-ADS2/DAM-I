import { Component, OnInit } from '@angular/core';
import { ListadoService } from '../../services/listado.service';
import { Penalizacion } from '../../models/Penalizacion';
import { DatePipe } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-penalizacion',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './penalizacion.component.html',
  styleUrl: './penalizacion.component.css'
})
export class PenalizacionComponent  implements OnInit{
  penalizaciones: Penalizacion[] = []
  
  constructor(private service: ListadoService){}
  ngOnInit(): void {
    this.service.listarPenalizacion().subscribe(arg => {
      this.penalizaciones = arg;
    })
  }

}
