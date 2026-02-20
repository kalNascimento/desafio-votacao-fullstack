import { render, screen } from '@testing-library/react'
import '@testing-library/jest-dom'
import { HomePage } from './home-page'

describe('HomePage component', () => {

  test('deve renderizar o texto "What\'s next?"', () => {
    render(<HomePage />)

    expect(screen.getByText("What's next?")).toBeInTheDocument()
  })

  test('deve renderizar os links de recursos', () => {
    render(<HomePage />)

    expect(screen.getByText('React Router Docs')).toBeInTheDocument()
    expect(screen.getByText('Join Discord')).toBeInTheDocument()
  })

  test('deve renderizar imagens com alt "React Router"', () => {
    render(<HomePage />)

    const images = screen.getAllByAltText('React Router')
    expect(images.length).toBeGreaterThan(0)
  })

  test('links devem ter href correto', () => {
    render(<HomePage />)

    const docsLink = screen.getByText('React Router Docs').closest('a')
    const discordLink = screen.getByText('Join Discord').closest('a')

    expect(docsLink).toHaveAttribute('href', 'https://reactrouter.com/docs')
    expect(discordLink).toHaveAttribute('href', 'https://rmx.as/discord')
  })

})
