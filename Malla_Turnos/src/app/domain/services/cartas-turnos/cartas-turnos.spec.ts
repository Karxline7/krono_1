import { TestBed } from '@angular/core/testing';

import { CartasTurnos } from './cartas-turnos';

describe('CartasTurnos', () => {
  let service: CartasTurnos;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CartasTurnos);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
