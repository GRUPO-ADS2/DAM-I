import { Routes } from '@angular/router';
import { SolicitudComponent } from './listados/components/solicitud/solicitud.component';
import { PenalizacionComponent } from './listados/components/penalizacion/penalizacion.component';
import { PrestamoComponent } from './listados/components/prestamo/prestamo.component';

export const routes: Routes = [
    { path: '', redirectTo: '/solicitudes', pathMatch: 'full' },  // Ruta para la página principal
    { path: 'solicitudes', component: SolicitudComponent },  // Ruta para la página de "About"
    { path: 'penalizaciones', component: PenalizacionComponent },
    { path: 'prestamos', component: PrestamoComponent }
];
