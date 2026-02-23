import { render, screen, fireEvent, waitFor } from "@testing-library/react"
import { FormCriarPauta, FormDataCriarPauta } from "./form-criar-pauta"

describe("FormCriarPauta", () => {
  const fillForm = () => {
    fireEvent.change(screen.getByPlaceholderText("Digite o nome"), {
      target: { value: "Pauta Teste" },
    })

    fireEvent.change(screen.getByPlaceholderText("Digite a descrição"), {
      target: { value: "Descrição Teste" },
    })
  }

  it("deve renderizar campos e botões", () => {
    render(<FormCriarPauta onSubmit={jest.fn()} />)

    expect(screen.getByText("Criar Pauta")).toBeInTheDocument()
    expect(screen.getByPlaceholderText("Digite o nome")).toBeInTheDocument()
    expect(screen.getByPlaceholderText("Digite a descrição")).toBeInTheDocument()
    expect(screen.getByText("Criar")).toBeInTheDocument()
    expect(screen.getByText("Cancelar")).toBeInTheDocument()
  })

  it("deve atualizar inputs ao digitar", () => {
    render(<FormCriarPauta onSubmit={jest.fn()} />)

    fillForm()

    expect(screen.getByDisplayValue("Pauta Teste")).toBeInTheDocument()
    expect(screen.getByDisplayValue("Descrição Teste")).toBeInTheDocument()
  })

  it("deve chamar onSubmit com dados corretos", async () => {
    const onSubmit = jest.fn().mockResolvedValue(undefined)

    render(<FormCriarPauta onSubmit={onSubmit} />)

    fillForm()

    fireEvent.click(screen.getByText("Criar"))

    await waitFor(() => {
      expect(onSubmit).toHaveBeenCalledWith({
        nome: "Pauta Teste",
        descricao: "Descrição Teste",
      })
    })
  })

  it("deve mostrar loading ao enviar", async () => {
    const onSubmit = jest.fn<Promise<void>, [FormDataCriarPauta]>()
      .mockResolvedValue(undefined)

    render(<FormCriarPauta onSubmit={onSubmit} />)

    fillForm()

    fireEvent.click(screen.getByText("Criar"))

    expect(screen.getByText("Enviando...")).toBeInTheDocument()

    await waitFor(() => {
      expect(screen.getByText("Criar")).toBeInTheDocument()
    })
  })

  it("deve chamar onCancel ao clicar cancelar", () => {
    const onCancel = jest.fn()

    render(<FormCriarPauta onSubmit={jest.fn()} onCancel={onCancel} />)

    fireEvent.click(screen.getByText("Cancelar"))

    expect(onCancel).toHaveBeenCalled()
  })

  it("deve mostrar alerta quando onSubmit falhar", async () => {
    const alertSpy = jest.spyOn(window, "alert").mockImplementation(() => { })

    const onSubmit = jest.fn().mockRejectedValue(new Error("Erro"))

    render(<FormCriarPauta onSubmit={onSubmit} />)

    fillForm()

    fireEvent.click(screen.getByText("Criar"))

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith("Erro ao enviar")
    })

    alertSpy.mockRestore()
  })
})