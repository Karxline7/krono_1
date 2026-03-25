import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Prueba3 } from './prueba3';

describe('Prueba3', () => {
  let component: Prueba3;
  let fixture: ComponentFixture<Prueba3>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Prueba3],
    }).compileComponents();

    fixture = TestBed.createComponent(Prueba3);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
