	.text
	.file	"irFileName"
	.globl	main                    # -- Begin function main
	.p2align	4, 0x90
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:
	pushq	%rax
	.cfi_def_cfa_offset 16
	movl	$0, 4(%rsp)
	cmpl	$10, 4(%rsp)
	je	.LBB0_3
	.p2align	4, 0x90
.LBB0_2:                                # %label2
                                        # =>This Inner Loop Header: Depth=1
	movl	4(%rsp), %esi
	movl	$.L.1arg_str, %edi
	xorl	%eax, %eax
	callq	printf
	incl	4(%rsp)
	cmpl	$10, 4(%rsp)
	jne	.LBB0_2
.LBB0_3:                                # %label3
	xorl	%eax, %eax
	popq	%rcx
	retq
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cfi_endproc
                                        # -- End function
	.type	.L.1arg_str,@object     # @.1arg_str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.1arg_str:
	.asciz	"%d\n"
	.size	.L.1arg_str, 4


	.section	".note.GNU-stack","",@progbits
