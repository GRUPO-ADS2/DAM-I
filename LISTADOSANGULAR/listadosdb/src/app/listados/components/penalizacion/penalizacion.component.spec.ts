import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PenalizacionComponent } from './penalizacion.component';

describe('PenalizacionComponent', () => {
  let component: PenalizacionComponent;
  let fixture: ComponentFixture<PenalizacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PenalizacionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PenalizacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
