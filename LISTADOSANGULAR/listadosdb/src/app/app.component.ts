import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SolicitudComponent } from './listados/components/solicitud/solicitud.component';
import { PrestamoComponent } from './listados/components/prestamo/prestamo.component';
import { PenalizacionComponent } from './listados/components/penalizacion/penalizacion.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterLink,RouterLinkActive, SolicitudComponent,PrestamoComponent, PenalizacionComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'listadosdb';
}
