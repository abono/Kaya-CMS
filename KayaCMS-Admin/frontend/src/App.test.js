import { render, screen } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  render(<App />);
  const userNameElement = screen.getByText(/username/i);
  expect(userNameElement).toBeInTheDocument();

  const passwordElement = screen.getByText(/password/i);
  expect(passwordElement).toBeInTheDocument();
});
