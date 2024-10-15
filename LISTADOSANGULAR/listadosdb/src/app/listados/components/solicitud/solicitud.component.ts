import { Component, OnInit } from '@angular/core';
import { ListadoService } from '../../services/listado.service';
import { Solicitud } from '../../models/Solicitud';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-solicitud',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './solicitud.component.html',
  styleUrl: './solicitud.component.css'
})

export class SolicitudComponent implements OnInit {
  solicitudes: Solicitud[] = [];
  constructor(private service: ListadoService) {}
  ngOnInit(): void {
    this.service.listarSolicitudes()
      .subscribe(arg => {
        this.solicitudes = arg
      });
  }
}
