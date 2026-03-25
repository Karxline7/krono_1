import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartasTurnos } from './cartas-turnos';

describe('CartasTurnos', () => {
  let component: CartasTurnos;
  let fixture: ComponentFixture<CartasTurnos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartasTurnos],
    }).compileComponents();

    fixture = TestBed.createComponent(CartasTurnos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
